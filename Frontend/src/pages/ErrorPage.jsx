import { useRouteError, Link } from "react-router-dom";

function ErrorPage() {
  const error = useRouteError();

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <div className="max-w-md w-full text-center">
        <h1 className="text-9xl font-bold text-red-600">Oops!</h1>
        <h2 className="mt-4 text-3xl font-bold text-gray-900">
          Something went wrong
        </h2>
        <p className="mt-2 text-lg text-gray-600">
          {error.message || "An unexpected error has occurred."}
        </p>
        <div className="mt-2 text-sm text-gray-500">
          {error.statusText || error.status}
        </div>
        <Link
          to="/app/home"
          className="mt-6 inline-block px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
        >
          Try Going Home
        </Link>
      </div>
    </div>
  );
}

export default ErrorPage;
