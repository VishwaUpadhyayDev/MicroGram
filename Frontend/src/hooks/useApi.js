import { useState, useCallback } from 'react';
import { useDispatch } from 'react-redux';
import { logout } from '../features/authSlice';
import { authAPI } from '../utils/api';

export const useApi = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const dispatch = useDispatch();

  const apiCall = useCallback(async (apiFunction, ...args) => {
    setLoading(true);
    setError(null);
    
    try {
      const result = await apiFunction(...args);
      return result;
    } catch (err) {
      // If unauthorized, logout user
      if (err.message.includes('401') || err.message.includes('403')) {
        dispatch(logout());
      }
      setError(err.message);
      throw err;
    } finally {
      setLoading(false);
    }
  }, [dispatch]);

  const clearError = useCallback(() => {
    setError(null);
  }, []);

  return {
    loading,
    error,
    apiCall,
    clearError,
    // Direct API methods
    getProfile: useCallback(() => apiCall(authAPI.getProfile), [apiCall]),
    updateProfile: useCallback((data) => apiCall(authAPI.updateProfile, data), [apiCall]),
  };
};