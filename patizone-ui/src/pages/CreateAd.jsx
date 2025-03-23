import { yupResolver } from '@hookform/resolvers/yup';
import { Button, Container, FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import TextField from '@mui/material/TextField';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { useMutation, useQuery } from '@tanstack/react-query';
import React from 'react';
import { Controller, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router';
import { toast } from 'react-toastify';
import * as yup from 'yup';
import FormError from '../components/FormError';
import Spinner from '../components/Spinner';
import AdService from '../services/AdService';
import { useAdStore } from '../store/useAdStore';
function CreateAd() {
  const { isLoading } = useAdStore();
  const navigate = useNavigate();
  const { data: adTypes } = useQuery({
    queryKey: ['adTypes'],
    queryFn: AdService.getAdTypes
  });

  const { mutate } = useMutation({
    mutationFn: (request) => {
      useAdStore.setState({ isLoading: true });
      return AdService.save(request);
    },
    onSuccess: (data) => {
      toast.success('Ad Created Successfully');
      reset();
      navigate('/');
    },
    onError: (error) => {
      toast.error('Ad not created: ' + error.message);
    },
    onSettled: () => {
      useAdStore.setState({ isLoading: false });
    }
  });

  const schema = yup.object({
    title: yup.string().required(),
    address: yup.string().required(),
    description: yup.string().required(),
    animalType: yup.string().required(),
    animalName: yup.string(),
    animalBreed: yup.string(),
    animalAge: yup.number(),
    endDate: yup.date(),
    adType: yup.number().required()
  });

  const {
    register,
    control,
    handleSubmit,
    formState: { errors },
    reset
  } = useForm({ resolver: yupResolver(schema) });

  function createAd(data) {
    mutate({ ...data, adType: { id: data.adType } });
  }

  return (
    <Spinner active={isLoading}>
      <Container sx={{ mt: 2 }}>
        <form onSubmit={handleSubmit(createAd)} className="flex flex-col gap-6">
          <h3 className="text-center">Create Ad</h3>
          <FormError errors={errors} name="title" />
          <Controller
            name="title"
            {...register('title')}
            control={control}
            render={({ field }) => <TextField {...field} label="Title" />}
          />
          <FormError errors={errors} name="animalType" />
          <Controller
            name="animalType"
            {...register('animalType')}
            control={control}
            render={({ field }) => <TextField {...field} label="Animal Type" />}
          />
          <FormError errors={errors} name="animalName" />
          <Controller
            name="animalName"
            {...register('animalName')}
            control={control}
            render={({ field }) => <TextField {...field} label="Animal Name" />}
          />
          <FormError errors={errors} name="animalBreed" />
          <Controller
            name="animalBreed"
            {...register('animalBreed')}
            control={control}
            render={({ field }) => <TextField {...field} label="Animal Breed" />}
          />
          <FormError errors={errors} name="animalAge" />
          <Controller
            name="animalAge"
            {...register('animalAge')}
            control={control}
            render={({ field }) => <TextField {...field} type="number" label="Animal Age" />}
          />
          <FormError errors={errors} name="endDate" />
          <Controller
            name="endDate"
            {...register('endDate')}
            control={control}
            render={({ field }) => (
              <DatePicker {...field} label="End Date" disablePast format="DD/MM/YYYY" />
            )}
          />
          <FormError errors={errors} name="address" />
          <Controller
            name="address"
            {...register('address')}
            control={control}
            render={({ field }) => <TextField {...field} label="Address" />}
          />
          <FormError errors={errors} name="description" />
          <Controller
            name="description"
            {...register('description')}
            control={control}
            render={({ field }) => <TextField {...field} label="Description" />}
          />
          <FormError errors={errors} name="adType" />
          <Controller
            name="adType"
            {...register('adType')}
            control={control}
            render={({ field }) => (
              <FormControl fullWidth>
                <InputLabel id="ad-type-label">Ad Type</InputLabel>
                <Select {...field} labelId="ad-type-label" label="Ad Type">
                  {adTypes?.map((adType) => (
                    <MenuItem key={adType.id} value={adType.id}>
                      {adType.text}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            )}
          />
          <Button variant="contained" type="submit">
            Create
          </Button>
        </form>
      </Container>
    </Spinner>
  );
}

export default CreateAd;
