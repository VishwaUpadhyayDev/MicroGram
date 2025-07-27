import { useSelector } from "react-redux";

function Home() {
  const { user } = useSelector((state) => state.auth);

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-50">
      <h1 className="text-4xl font-bold text-gray-900">
        Welcome to MicroGram{user ? `, ${user.username}` : ""}
      </h1>
      {user && (
        <div className="mt-4 text-gray-600">
          Your token: {user.token.substring(0, 20)}...
        </div>
      )}
    </div>
  );
}

export default Home;
