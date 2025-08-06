import { createSlice } from "@reduxjs/toolkit";

const loadUserFromStorage = () => {
  try {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  } catch {
    return null;
  }
};

const initialState = {
  user: loadUserFromStorage(),
  isAuthenticated: !!loadUserFromStorage(),
  loading: false,
  error: null,
  registrationSuccess: false,
  sessionTimer: null,
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    loginStart: (state) => {
      state.loading = true;
      state.error = null;
    },
    loginSuccess: (state, action) => {
      state.loading = false;
      state.isAuthenticated = true;
      state.user = { ...action.payload, expiresAt: Date.now() + 15 * 60 * 1000 }; // 15 min mock expiry
      state.error = null;
      localStorage.setItem('user', JSON.stringify(state.user));
    },
    loginFailure: (state, action) => {
      state.loading = false;
      state.error = action.payload;
    },
    logout: (state) => {
      state.user = null;
      state.isAuthenticated = false;
      state.loading = false;
      state.error = null;
      state.sessionTimer = null;
      localStorage.removeItem('user');
    },
    renewSession: (state, action) => {
      if (state.user) {
        state.user = { ...state.user, ...action.payload, expiresAt: Date.now() + 15 * 60 * 1000 };
        localStorage.setItem('user', JSON.stringify(state.user));
      }
    },
    signupStart: (state) => {
      state.loading = true;
      state.error = null;
      state.registrationSuccess = false;
    },
    signupSuccess: (state) => {
      state.loading = false;
      state.error = null;
      state.registrationSuccess = true;
    },
    signupFailure: (state, action) => {
      state.loading = false;
      state.error = action.payload;
      state.registrationSuccess = false;
    },
    clearAuthState: (state) => {
      state.error = null;
      state.registrationSuccess = false;
    },
  },
});

export const {
  loginStart,
  loginSuccess,
  loginFailure,
  logout,
  signupStart,
  signupSuccess,
  signupFailure,
  clearAuthState,
  renewSession,
} = authSlice.actions;
export default authSlice.reducer;
