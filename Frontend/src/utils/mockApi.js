// Mock API calls with JWT handling
export const mockAuthAPI = {
  login: async (credentials) => {
    // Simulate API delay
    await new Promise((resolve) => setTimeout(resolve, 1000));

    if (credentials.username && credentials.password) {
      // Mock JWT token
      const token = `mock_jwt_${Math.random()
        .toString(36)
        .slice(2)}_${Date.now()}`;
      const user = {
        id: 1,
        username: credentials.username,
        email: `${credentials.username}@example.com`,
        token,
      };

      // Store in localStorage
      localStorage.setItem("user", JSON.stringify(user));
      return user;
    }
    throw new Error("Invalid credentials");
  },

  signup: async (userData) => {
    await new Promise((resolve) => setTimeout(resolve, 1000));

    if (userData.username && userData.password && userData.email) {
      const token = `mock_jwt_${Math.random()
        .toString(36)
        .slice(2)}_${Date.now()}`;
      const user = {
        id: Math.floor(Math.random() * 1000),
        username: userData.username,
        email: userData.email,
        token,
      };

      localStorage.setItem("user", JSON.stringify(user));
      return user;
    }
    throw new Error("Invalid user data");
  },

  logout: () => {
    localStorage.removeItem("user");
  },

  getCurrentUser: () => {
    const user = localStorage.getItem("user");
    return user ? JSON.parse(user) : null;
  },

  renewToken: async () => {
    await new Promise((resolve) => setTimeout(resolve, 500));
    const user = localStorage.getItem("user");
    if (user) {
      const userData = JSON.parse(user);
      const newToken = `mock_jwt_${Math.random().toString(36).slice(2)}_${Date.now()}`;
      return { ...userData, token: newToken };
    }
    throw new Error("No user found");
  },
};
