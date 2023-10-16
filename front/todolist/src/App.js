import './App.css';
import RootLayout from './components/RootLayout/RootLayout';
import { Route, Routes } from 'react-router-dom';
import Main from './pages/main/Main';
import Signin from './pages/signin/Signin';
import Signup from './pages/signup/Signup';
import AuthRoute from './auth/AuthRoute'

function App() {
  return (
    <RootLayout>
      <Routes>
        <Route path="/" element={<AuthRoute element={<Main/>}/>}/>
        <Route path="/auth/signin" element={<AuthRoute element={<Signin/>}/>}/>
        <Route path="/auth/signup" element={<AuthRoute element={<Signup/>}/>}/>
      </Routes>
    </RootLayout>
  );
}

export default App;
