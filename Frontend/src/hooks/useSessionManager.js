import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { renewSession, logout } from '../features/authSlice';
import { mockAuthAPI } from '../utils/mockApi';

export const useSessionManager = () => {
  const dispatch = useDispatch();
  const { user, isAuthenticated } = useSelector(state => state.auth);

  useEffect(() => {
    if (!isAuthenticated || !user?.expiresAt) return;

    const checkAndRenewSession = async () => {
      const timeUntilExpiry = user.expiresAt - Date.now();
      
      if (timeUntilExpiry <= 0) {
        dispatch(logout());
        return;
      }

      // Renew 2 minutes before expiry
      if (timeUntilExpiry <= 2 * 60 * 1000) {
        try {
          const renewedUser = await mockAuthAPI.renewToken();
          dispatch(renewSession(renewedUser));
        } catch {
          dispatch(logout());
        }
      }
    };

    checkAndRenewSession();
    const interval = setInterval(checkAndRenewSession, 60 * 1000); // Check every minute

    return () => clearInterval(interval);
  }, [dispatch, user, isAuthenticated]);
};