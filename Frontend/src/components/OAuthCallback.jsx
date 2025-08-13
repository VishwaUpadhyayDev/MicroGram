import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { loginSuccess, loginFailure } from '../features/authSlice';
import { authAPI } from '../utils/api';

const OAuthCallback = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [searchParams] = useSearchParams();

  useEffect(() => {
    const handleOAuthCallback = async () => {
      const token = searchParams.get('token');
      const error = searchParams.get('error');

      if (error) {
        dispatch(loginFailure('OAuth authentication failed'));
        navigate('/login');
        return;
      }

      if (token) {
        try {
          // Store token temporarily and fetch user profile
          localStorage.setItem('user', JSON.stringify({ token }));
          const profile = await authAPI.getProfile();
          
          const userData = {
            ...profile,
            token,
          };
          
          dispatch(loginSuccess(userData));
          navigate('/app/home');
        } catch (err) {
          dispatch(loginFailure('Failed to fetch user profile'));
          navigate('/login');
        }
      } else {
        dispatch(loginFailure('No token received'));
        navigate('/login');
      }
    };

    handleOAuthCallback();
  }, [searchParams, dispatch, navigate]);

  return (
    <div className="min-h-screen flex items-center justify-center">
      <div className="text-center">
        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600 mx-auto"></div>
        <p className="mt-4">Completing authentication...</p>
      </div>
    </div>
  );
};

export default OAuthCallback;