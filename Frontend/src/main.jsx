import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { Provider } from "react-redux";
import { RouterProvider, createBrowserRouter, Navigate } from "react-router-dom";
import { store } from "./store";
import "./index.css";

// Routes
import App from "./App.jsx";
import { Login, Signup, Home, NotFound, ErrorPage, Profile } from "./pages";
import ProtectedRoute from "./components/ProtectedRoute";
import OAuthCallback from "./components/OAuthCallback";


const router = createBrowserRouter([
  {
    path: "/",
    element: <ProtectedRoute />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "app",
        element: <App />,
        children: [
          {
            index: true,
            element: <Navigate to="home" replace />,
          },
          {
            path: "home",
            element: <Home />,
          },
          {
            path: "profile",
            element: <Profile />,
          },
        ],
      },
      {
        path: "login",
        element: <Login />,
      },
      {
        path: "signup",
        element: <Signup />,
      },
      {
        path: "oauth/callback",
        element: <OAuthCallback />,
      },
      {
        path: "*",
        element: <NotFound />,
      },
    ],
  },
]);

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <Provider store={store}>
      <RouterProvider router={router} />
    </Provider>
  </StrictMode>
);
