import { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, Link } from "react-router-dom";
import {
  signupStart,
  signupSuccess,
  signupFailure,
  clearAuthState,
} from "../features/authSlice";
import { mockAuthAPI } from "../utils/mockApi";

function Signup() {
  const [userData, setUserData] = useState({
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { loading, error, registrationSuccess } = useSelector(
    (state) => state.auth
  );

  useEffect(() => {
    dispatch(clearAuthState());
  }, [dispatch]);

  useEffect(() => {
    if (registrationSuccess) {
      navigate("/login");
    }
  }, [registrationSuccess, navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (userData.password !== userData.confirmPassword) {
      dispatch(signupFailure("Passwords do not match"));
      return;
    }

    dispatch(signupStart());

    try {
      await mockAuthAPI.signup(userData);
      dispatch(signupSuccess());
    } catch (err) {
      dispatch(signupFailure(err.message));
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center px-4" style={{ backgroundColor: 'var(--bg)' }}>
      <div className="w-full max-w-sm">
        <div className="text-center mb-8">
          <h1 className="text-2xl font-semibold mb-2" style={{ color: 'var(--text)' }}>
            Join MicroGram
          </h1>
          <p className="text-sm" style={{ color: 'var(--text-muted)' }}>
            Start meaningful conversations today
          </p>
        </div>
        
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <input
              type="text"
              required
              placeholder="Username"
              value={userData.username}
              onChange={(e) => setUserData(prev => ({ ...prev, username: e.target.value }))}
              className="w-full px-3 py-2 rounded-md border transition-colors"
              style={{
                backgroundColor: 'var(--bg-light)',
                borderColor: 'var(--border)',
                color: 'var(--text)'
              }}
            />
          </div>
          
          <div>
            <input
              type="email"
              required
              placeholder="Email address"
              value={userData.email}
              onChange={(e) => setUserData(prev => ({ ...prev, email: e.target.value }))}
              className="w-full px-3 py-2 rounded-md border transition-colors"
              style={{
                backgroundColor: 'var(--bg-light)',
                borderColor: 'var(--border)',
                color: 'var(--text)'
              }}
            />
          </div>
          
          <div>
            <input
              type="password"
              required
              placeholder="Password"
              value={userData.password}
              onChange={(e) => setUserData(prev => ({ ...prev, password: e.target.value }))}
              className="w-full px-3 py-2 rounded-md border transition-colors"
              style={{
                backgroundColor: 'var(--bg-light)',
                borderColor: 'var(--border)',
                color: 'var(--text)'
              }}
            />
          </div>
          
          <div>
            <input
              type="password"
              required
              placeholder="Confirm password"
              value={userData.confirmPassword}
              onChange={(e) => setUserData(prev => ({ ...prev, confirmPassword: e.target.value }))}
              className="w-full px-3 py-2 rounded-md border transition-colors"
              style={{
                backgroundColor: 'var(--bg-light)',
                borderColor: 'var(--border)',
                color: 'var(--text)'
              }}
            />
          </div>

          {error && (
            <div className="text-sm text-center" style={{ color: 'var(--danger)' }}>
              {error}
            </div>
          )}

          <button
            type="submit"
            disabled={loading}
            className="w-full py-2 px-4 rounded-md font-medium transition-colors disabled:opacity-50"
            style={{
              backgroundColor: 'var(--primary)',
              color: 'var(--bg-light)',
              border: '1px solid var(--primary)'
            }}
          >
            {loading ? "Creating account..." : "Create account"}
          </button>
        </form>

        <div className="text-center mt-6">
          <Link to="/login" className="text-sm" style={{ color: 'var(--primary)' }}>
            Already have an account? Sign in
          </Link>
        </div>
      </div>
    </div>
  );
}

export default Signup;
