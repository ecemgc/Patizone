import React, { useEffect } from "react";
import { useAuthStore } from "../store/useAuthStore";
import { Link, useNavigate } from "react-router";
import {
  MDBBtn,
  MDBContainer,
  MDBRow,
  MDBCol,
  MDBCard,
  MDBCardBody,
  MDBInput,
} from "mdb-react-ui-kit";
import { useMutation } from "@tanstack/react-query";
import AuthService from "../services/AuthService";
import Spinner from "../components/Spinner";
import { Controller, useForm } from "react-hook-form";
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import FormError from "../components/FormError";
import { toast } from "react-toastify";

function Login() {
  const { user, setAuth, isLoading } = useAuthStore();
  const navigate = useNavigate();

  const { mutate } = useMutation({
    mutationFn: (request) => {
      useAuthStore.setState({ isLoading: true });
      return AuthService.login(request);
    },
    onSuccess: (data) => {
      toast.success("Login Success");
      const user = {
        token: data.token,
        id: data.user.id,
        firstName: data.user.firstName,
        lastName: data.user.lastName,
        email: data.user.email,
        imageUrl: data.user.imageUrl,
      };
      setAuth(user); // Kullanıcıyı ve token'ı Zustand'a kaydet
    },
    onError: (error) => {
      toast.error("Login Fail", error.message);
    },
    onSettled: () => {
      useAuthStore.setState({ isLoading: false });
    },
  });

  useEffect(() => {
    if (user) {
      navigate("/");
    }
  }, [user, navigate]);

  function handleLogin(data) {
    mutate(data);
  }

  const schema = yup
    .object({
      email: yup.string().email().min(8).max(100).required(),
      password: yup.string().min(3).max(100).required(),
    })
    .required();

  const {
    register,
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({ resolver: yupResolver(schema) });

  return (
    <Spinner active={isLoading}>
      <MDBContainer className="p-4">
        <MDBRow>
          <MDBCol
            md="6"
            className="text-center text-md-start d-flex flex-column justify-content-center"
          >
            <h1 className="my-5 display-3 fw-bold ls-tight px-3">
              Patizone <br />
              <span className="text-primary">Pet Lovers</span>
            </h1>

            <p className="px-3" style={{ color: "hsl(217, 10%, 50.8%)" }}>
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Eveniet,
              itaque accusantium odio, soluta, corrupti aliquam quibusdam
              tempora at cupiditate quis eum maiores libero veritatis? Dicta
              facilis sint aliquid ipsum atque?
            </p>
          </MDBCol>

          <MDBCol md="6">
            <MDBCard className="my-5">
              <MDBCardBody className="p-5">
                <form onSubmit={handleSubmit(handleLogin)}>
                  <FormError errors={errors} name="email" />
                  <Controller
                    name="email"
                    {...register("email")}
                    control={control}
                    render={({ field }) => (
                      <MDBInput
                        {...field}
                        wrapperClass="mb-4"
                        label="Email"
                        id="form1"
                      />
                    )}
                  />

                  <FormError errors={errors} name="password" />
                  <Controller
                    name="password"
                    {...register("password")}
                    control={control}
                    render={({ field }) => (
                      <MDBInput
                        {...field}
                        wrapperClass="mb-4"
                        label="Password"
                        id="form1"
                        type="password"
                      />
                    )}
                  />

                  <MDBBtn className="w-100 mb-4" size="md" type="submit">
                    sign in
                  </MDBBtn>
                </form>

                <p className="text-center">
                  Don't have an account? <Link to="/register"> Sign up</Link>
                </p>
              </MDBCardBody>
            </MDBCard>
          </MDBCol>
        </MDBRow>
      </MDBContainer>
    </Spinner>
  );
}

export default Login;
