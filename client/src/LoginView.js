import React, { useState } from 'react'
import styled from '@emotion/styled'

const LoginViewRoot = styled.div`
  flex-direction: column;
  align-items: center;
  height: 500px;
  width: 500px;
`
const LoginForm = styled.form`
  margin: 0 auto;
  flex-direction: column;
  align-items: center;
`
const Input = styled.input``
const InputName = styled.label``

const SubmitButton = styled.button``

const LoginView = () => {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  return (
    <LoginViewRoot>
      <LoginForm>
        <InputName>email</InputName>
        <Input
          type='text'
          onChange={e => setEmail(e.currentTarget.value)}
          value={email}
        />
        <InputName>password</InputName>
        <Input
          type='password'
          onChange={e => setPassword(e.currentTarget.value)}
          value={password}
        />
        <SubmitButton>Login</SubmitButton>
      </LoginForm>
    </LoginViewRoot>
  )
}

export default LoginView
