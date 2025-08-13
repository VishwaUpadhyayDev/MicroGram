import { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { User, LogOut, Settings } from "lucide-react";
import { logout } from "../../features/authSlice";

function Header() {
  const { user } = useSelector((state) => state.auth);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const handleLogout = () => {
    dispatch(logout());
    navigate("/login");
    setIsDropdownOpen(false);
  };

  return (
    <header
      style={{
        backgroundColor: "var(--bg-light)",
        borderBottom: "1px solid var(--border)",
      }}
    >
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16 items-center">
          <div className="flex-shrink-0">
            <h1
              className="text-xl font-semibold"
              style={{ color: "var(--text)" }}
            >
              MicroGram
            </h1>
          </div>
          <div className="flex items-center space-x-4 relative">
            <span style={{ color: "var(--text-muted)" }}>
              Welcome, {user?.username}
            </span>
            <button
              onClick={() => setIsDropdownOpen(!isDropdownOpen)}
              className="p-2 rounded-full transition-colors"
              style={{
                backgroundColor: isDropdownOpen
                  ? "var(--highlight)"
                  : "transparent",
                color: "var(--text)",
              }}
            >
              <User size={20} />
            </button>

            {/* Dropdown Drawer */}
            {isDropdownOpen && (
              <div
                className="absolute top-full right-0 mt-2 w-48 rounded-md shadow-lg z-50"
                style={{
                  backgroundColor: "var(--bg-light)",
                  border: "1px solid var(--border)",
                }}
              >
                <div className="py-1">
                  <button
                    onClick={() => {
                      navigate("/app/profile");
                      setIsDropdownOpen(false);
                    }}
                    className="w-full px-4 py-2 text-left text-sm flex items-center space-x-2 hover:bg-[var(--highlight)] transition-colors"
                    style={{ color: "var(--text)" }}
                  >
                    <Settings size={16} />
                    <span>Profile</span>
                  </button>
                  <button
                    onClick={handleLogout}
                    className="w-full px-4 py-2 text-left text-sm flex items-center space-x-2 hover:bg-[var(--highlight)] transition-colors"
                    style={{ color: "var(--text)" }}
                  >
                    <LogOut size={16} />
                    <span>Logout</span>
                  </button>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </header>
  );
}

export default Header;
