import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { logout } from '../features/authSlice';
import { authAPI } from '../utils/api';

export const useSessionManager = () => {
  const dispatch = useDispatch();
  const { user, isAuthenticated } = useSelector(state => state.auth);

  useEffect(() => {
    if (!isAuthenticated || !user?.token) return;

    const checkTokenValidity = async () => {
      try {
        // Try to fetch user profile to validate token
        await authAPI.getProfile();
      } catch (error) {
        // If token is invalid, logout
        if (error.message.includes('401') || error.message.includes('403')) {
          dispatch(logout());
        }
      }
    };

    // Check token validity every 5 minutes
    const interval = setInterval(checkTokenValidity, 5 * 60 * 1000);

    return () => clearInterval(interval);
  }, [dispatch, user, isAuthenticated]);
};