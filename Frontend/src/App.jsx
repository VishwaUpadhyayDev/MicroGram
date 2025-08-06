import { Outlet } from "react-router-dom";
import "./App.css";
import { useSessionManager } from "./hooks/useSessionManager";
import Header from "./components/Header/Header";

function App() {
  useSessionManager();

  return (
    <>
      <Header />
      <Outlet />
    </>
  );
}

export default App;
