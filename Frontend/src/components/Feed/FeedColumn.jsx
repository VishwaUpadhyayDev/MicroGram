import { useSelector } from "react-redux";

function FeedColumn() {
  const { user } = useSelector((state) => state.auth);

  return (
    <section className="flex-1 flex flex-col">
      <header className="p-4 border-b border-[var(--border)] bg-[var(--bg-light)]">
        <h1 className="text-xl font-semibold" style={{ color: "var(--text)" }}>
          Feed
        </h1>
      </header>
      <div className="flex-1 overflow-y-auto p-6">
        <div className="text-center py-8">
          <h2 className="text-2xl font-semibold mb-2" style={{ color: "var(--text)" }}>
            Welcome to MicroGram{user ? `, ${user.username}` : ""}
          </h2>
          <p className="text-lg" style={{ color: "var(--text-muted)" }}>
            Your space for meaningful conversations
          </p>
        </div>
      </div>
    </section>
  );
}

export default FeedColumn;