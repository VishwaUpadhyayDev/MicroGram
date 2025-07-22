import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { loginStart, loginSuccess, loginFailure } from "../features/authSlice";

function Login() {
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
  });
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { loading, error } = useSelector((state) => state.auth);

  const handleSubmit = async (e) => {
    e.preventDefault();
    dispatch(loginStart());

    try {
      // Replace this with your actual API call
      // const response = await loginAPI(credentials);
      // dispatch(loginSuccess(response.data));
      dispatch(loginSuccess({ username: credentials.username })); // Temporary mock
      navigate("/");
    } catch (err) {
      dispatch(loginFailure(err.message));
    }
  };

  return (
    <div>
      <h2 className="text-2xl">Login to MicroGram</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <input
            type="text"
            placeholder="Username"
            value={credentials.username}
            onChange={(e) =>
              setCredentials((prev) => ({ ...prev, username: e.target.value }))
            }
          />
        </div>
        <div>
          <input
            type="password"
            placeholder="Password"
            value={credentials.password}
            onChange={(e) =>
              setCredentials((prev) => ({ ...prev, password: e.target.value }))
            }
          />
        </div>
        <button type="submit" disabled={loading}>
          {loading ? "Logging in..." : "Login"}
        </button>
        {error && <p style={{ color: "red" }}>{error}</p>}
      </form>
    </div>
  );
}

export default Login;
