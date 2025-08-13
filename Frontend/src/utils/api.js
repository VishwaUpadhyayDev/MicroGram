const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';
const BACKEND_URL = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';

// API utility functions
const apiRequest = async (url, options = {}) => {
  const token = getStoredToken();
  
  const config = {
    headers: {
      'Content-Type': 'application/json',
      ...(token && { Authorization: `Bearer ${token}` }),
      ...options.headers,
    },
    ...options,
  };

  const response = await fetch(`${API_BASE_URL}${url}`, config);
  
  if (!response.ok) {
    const error = await response.text();
    throw new Error(error || `HTTP ${response.status}`);
  }
  
  return response.json();
};

const getStoredToken = () => {
  const user = localStorage.getItem('user');
  return user ? JSON.parse(user).token : null;
};

const storeUser = (userData) => {
  localStorage.setItem('user', JSON.stringify(userData));
};

// Real API calls
export const authAPI = {
  login: async (credentials) => {
    const loginData = {
      usernameOrEmail: credentials.username,
      password: credentials.password,
    };
    
    const response = await apiRequest('/auth/login', {
      method: 'POST',
      body: JSON.stringify(loginData),
    });
    
    const userData = {
      username: response.username,
      token: response.accessToken,
    };
    
    storeUser(userData);
    return userData;
  },

  signup: async (userData) => {
    await apiRequest('/auth/signup', {
      method: 'POST',
      body: JSON.stringify(userData),
    });
    return { success: true };
  },

  logout: () => {
    localStorage.removeItem('user');
  },

  getCurrentUser: () => {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  },

  getProfile: async () => {
    const profile = await apiRequest('/profile/me');
    return {
      id: profile.id,
      username: profile.username,
      email: profile.email,
      displayName: profile.displayName,
      bio: profile.bio,
      profilePictureUrl: profile.profilePictureUrl,
      followerCount: profile.followerCount,
      followingCount: profile.followingCount,
      postCount: profile.postCount,
    };
  },

  updateProfile: async (profileData) => {
    const updated = await apiRequest('/profile/me', {
      method: 'PUT',
      body: JSON.stringify(profileData),
    });
    return {
      id: updated.id,
      username: updated.username,
      email: updated.email,
      displayName: updated.displayName,
      bio: updated.bio,
      profilePictureUrl: updated.profilePictureUrl,
      followerCount: updated.followerCount,
      followingCount: updated.followingCount,
      postCount: updated.postCount,
    };
  },

  // Google OAuth
  initiateGoogleLogin: () => {
    window.location.href = `${BACKEND_URL}/oauth2/authorize/google`;
  },
};

// Keep mockAuthAPI for backward compatibility during transition
export const mockAuthAPI = authAPI;
