import { Routes, Route } from "react-router-dom";
import { useSelector } from "react-redux";
import "./App.css";
import Login from "./pages/Login";
import ProtectedRoute from "./components/ProtectedRoute";

function Home() {
  const { user } = useSelector((state) => state.auth);

  return (
    <div>
      <h1>Welcome to MicroGram{user ? `, ${user.username}` : ""}</h1>
    </div>
  );
}

function App() {
  return (
    <div className="app">
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route
          path="/"
          element={
            <ProtectedRoute>
              <Home />
            </ProtectedRoute>
          }
        />
      </Routes>
    </div>
  );
}

export default App;
