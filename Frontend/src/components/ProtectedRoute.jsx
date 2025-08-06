import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useSelector } from "react-redux";

function ProtectedRoute() {
  const { isAuthenticated } = useSelector((state) => state.auth);
  const location = useLocation();
  const path = location.pathname;

  // Always redirect root to either home or login
  if (path === "/") {
    return <Navigate to={isAuthenticated ? "/app/home" : "/login"} replace />;
  }

  // If on auth pages (login/signup) and authenticated, redirect to home
  if (isAuthenticated && ["/login", "/signup"].includes(path)) {
    return <Navigate to="/app/home" replace />;
  }

  // If trying to access app routes without auth, redirect to login
  if (!isAuthenticated && path.startsWith("/app")) {
    return <Navigate to="/login" replace />;
  }

  return <Outlet />;
}

export default ProtectedRoute;
