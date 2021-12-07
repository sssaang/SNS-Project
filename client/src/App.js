import { Switch, Route, Redirect } from 'react-router-dom'
import HomeView from './HomeView'
import LoginView from './LoginView'

function App() {
  return (
    <Switch className='App'>
      <Route path='/' exact>
        <HomeView />
      </Route>
      <Route path='/login'>
        <LoginView />
      </Route>
    </Switch>
  )
}

export default App
