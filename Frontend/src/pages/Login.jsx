import { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, Link } from "react-router-dom";
import {
  loginStart,
  loginSuccess,
  loginFailure,
  clearAuthState,
} from "../features/authSlice";
import { authAPI } from "../utils/api";
import BubbleBackground from "../components/BubbleBackground";

function Login() {
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
  });

  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { loading, error, isAuthenticated } = useSelector(
    (state) => state.auth
  );

  useEffect(() => {
    if (isAuthenticated) {
      navigate("/app/home");
    }
  }, [isAuthenticated, navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    dispatch(loginStart());

    try {
      const user = await authAPI.login(credentials);
      dispatch(loginSuccess(user));
    } catch (err) {
      dispatch(loginFailure(err.message));
    }
  };

  const handleGoogleLogin = () => {
    authAPI.initiateGoogleLogin();
  };

  return (
    <div
      className="min-h-screen flex items-center justify-center px-4 relative overflow-hidden"
      style={{ backgroundColor: "var(--bg)" }}
    >
      <BubbleBackground bubbleCount={50} />

      <div className="w-full max-w-sm relative z-10">
        <div className="text-center mb-8">
          <h1
            className="text-2xl font-semibold mb-2"
            style={{ color: "var(--text)" }}
          >
            Welcome back
          </h1>
          <p className="text-sm" style={{ color: "var(--text-muted)" }}>
            Sign in to continue your conversations
          </p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <input
              type="text"
              required
              placeholder="Username"
              value={credentials.username}
              onChange={(e) =>
                setCredentials((prev) => ({
                  ...prev,
                  username: e.target.value,
                }))
              }
              className="w-full px-3 py-2 rounded-md border transition-colors"
              style={{
                backgroundColor: "var(--bg-light)",
                borderColor: "var(--border)",
                color: "var(--text)",
              }}
            />
          </div>

          <div>
            <input
              type="password"
              required
              placeholder="Password"
              value={credentials.password}
              onChange={(e) =>
                setCredentials((prev) => ({
                  ...prev,
                  password: e.target.value,
                }))
              }
              className="w-full px-3 py-2 rounded-md border transition-colors"
              style={{
                backgroundColor: "var(--bg-light)",
                borderColor: "var(--border)",
                color: "var(--text)",
              }}
            />
          </div>

          {error && (
            <div
              className="text-sm text-center"
              style={{ color: "var(--danger)" }}
            >
              {error}
            </div>
          )}

          <button
            type="submit"
            disabled={loading}
            className="w-full py-2 px-4 rounded-md font-medium transition-colors disabled:opacity-50"
            style={{
              backgroundColor: "var(--primary)",
              color: "var(--bg-light)",
              border: "1px solid var(--primary)",
            }}
          >
            {loading ? "Signing in..." : "Sign in"}
          </button>

          <div className="relative my-4">
            <div className="absolute inset-0 flex items-center">
              <div className="w-full border-t" style={{ borderColor: "var(--border)" }}></div>
            </div>
            <div className="relative flex justify-center text-sm">
              <span className="px-2" style={{ backgroundColor: "var(--bg)", color: "var(--text-muted)" }}>Or</span>
            </div>
          </div>

          <button
            type="button"
            onClick={handleGoogleLogin}
            className="w-full py-2 px-4 rounded-md font-medium transition-colors border"
            style={{
              backgroundColor: "var(--bg-light)",
              color: "var(--text)",
              borderColor: "var(--border)",
            }}
          >
            Continue with Google
          </button>
        </form>

        <div className="text-center mt-6">
          <Link
            to="/signup"
            className="text-sm"
            style={{ color: "var(--primary)" }}
          >
            New to MicroGram? Create an account
          </Link>
        </div>
      </div>
    </div>
  );
}

export default Login;
