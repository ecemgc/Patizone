import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import {
  MDBBtn,
  MDBCard,
  MDBCardBody,
  MDBCol,
  MDBContainer,
  MDBIcon,
  MDBInput,
  MDBRow
} from 'mdb-react-ui-kit';
import { useEffect } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { Link, useNavigate } from 'react-router';
import { toast } from 'react-toastify';
import * as yup from 'yup';
import FormError from '../components/FormError';
import Spinner from '../components/Spinner';
import AuthService from '../services/AuthService';
import { useAuthStore } from '../store/useAuthStore';

const phoneRegExp =
  /^((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?$/;

function Register() {
  const { user, isLoading, setAuth } = useAuthStore(); // Kullanıcı oturum durumu
  const navigate = useNavigate();

  const { mutate } = useMutation({
    mutationFn: (request) => {
      return AuthService.register(request);
    },
    onSuccess: (data) => {
      toast.success('Register Success');
      const user = {
        token: data.token,
        id: data.user.id,
        firstName: data.user.firstName,
        lastName: data.user.lastName,
        email: data.user.email,
        imageUrl: data.user.imageUrl
      };
      setAuth(user);
    },
    onError: (error) => {
      toast.error('Register Fail', error.message);
    },
    onSettled: () => {
      useAuthStore.setState({ isLoading: false });
    }
  });

  useEffect(() => {
    if (user) {
      navigate('/'); // Eğer giriş yapılmışsa homepage'e yönlendir
    }
  }, [user, navigate]);

  const schema = yup
    .object({
      email: yup.string().email().min(8).max(100).required(),
      password: yup.string().min(3).max(100).required(),
      firstName: yup.string().min(3).max(100).required(),
      lastName: yup.string().min(3).max(100).required(),
      phone: yup.string().matches(phoneRegExp, 'Phone number is not valid')
    })
    .required();

  const {
    register,
    control,
    handleSubmit,
    formState: { errors }
  } = useForm({ resolver: yupResolver(schema) });

  function handleRegister(data) {
    mutate(data);
  }

  return (
    <Spinner active={isLoading}>
      <MDBContainer fluid className="p-4">
        <MDBRow>
          <MDBCol
            md="6"
            className="text-center text-md-start d-flex flex-column justify-content-center">
            <h1 className="my-5 display-3 fw-bold ls-tight px-3">
              The best offer <br />
              <span className="text-primary">for your business</span>
            </h1>

            <p className="px-3" style={{ color: 'hsl(217, 10%, 50.8%)' }}>
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Eveniet, itaque accusantium
              odio, soluta, corrupti aliquam quibusdam tempora at cupiditate quis eum maiores libero
              veritatis? Dicta facilis sint aliquid ipsum atque?
            </p>
          </MDBCol>

          <MDBCol md="6">
            <MDBCard className="my-5">
              <MDBCardBody className="p-5">
                <form onSubmit={handleSubmit(handleRegister)}>
                  <FormError errors={errors} name="firstName" />
                  <Controller
                    name="firstName"
                    {...register('firstName')}
                    control={control}
                    render={({ field }) => (
                      <MDBInput {...field} wrapperClass="mb-4" label="First Name" id="firstName" />
                    )}
                  />
                  <FormError errors={errors} name="lastName" />
                  <Controller
                    name="lastName"
                    {...register('lastName')}
                    control={control}
                    render={({ field }) => (
                      <MDBInput {...field} wrapperClass="mb-4" label="Last Name" id="lastName" />
                    )}
                  />

                  <FormError errors={errors} name="email" />
                  <Controller
                    name="email"
                    {...register('email')}
                    control={control}
                    render={({ field }) => (
                      <MDBInput {...field} wrapperClass="mb-4" label="Email" id="email" />
                    )}
                  />

                  <FormError errors={errors} name="password" />
                  <Controller
                    name="password"
                    {...register('password')}
                    control={control}
                    render={({ field }) => (
                      <MDBInput
                        {...field}
                        wrapperClass="mb-4"
                        label="Password"
                        id="password"
                        type="password"
                      />
                    )}
                  />

                  <Controller
                    name="phone"
                    {...register('phone')}
                    control={control}
                    render={({ field }) => (
                      <MDBInput {...field} wrapperClass="mb-4" label="Phone Number" id="phone" />
                    )}
                  />

                  <MDBBtn className="w-100 mb-4" size="md" type="submit">
                    sign in
                  </MDBBtn>
                </form>

                <p className="text-center">
                  Do you have an account? <Link to="/login">Sign in</Link>
                </p>

                <div className="text-center">
                  <p>or sign up with:</p>

                  <MDBBtn tag="a" color="none" className="mx-3" style={{ color: '#1266f1' }}>
                    <MDBIcon fab icon="facebook-f" size="sm" />
                  </MDBBtn>

                  <MDBBtn tag="a" color="none" className="mx-3" style={{ color: '#1266f1' }}>
                    <MDBIcon fab icon="twitter" size="sm" />
                  </MDBBtn>

                  <MDBBtn tag="a" color="none" className="mx-3" style={{ color: '#1266f1' }}>
                    <MDBIcon fab icon="google" size="sm" />
                  </MDBBtn>

                  <MDBBtn tag="a" color="none" className="mx-3" style={{ color: '#1266f1' }}>
                    <MDBIcon fab icon="github" size="sm" />
                  </MDBBtn>
                </div>
              </MDBCardBody>
            </MDBCard>
          </MDBCol>
        </MDBRow>
      </MDBContainer>
    </Spinner>
  );
}

export default Register;
