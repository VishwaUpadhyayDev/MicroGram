function ChatSidebar() {
  return (
    <section className="border-r border-[var(--border)] bg-[var(--bg-light)] flex flex-col">
      <header className="p-4 border-b border-[var(--border)]">
        <h2 className="text-lg font-semibold" style={{ color: "var(--text)" }}>
          Chats
        </h2>
      </header>
      <div className="flex-1 overflow-y-auto p-4">
        <div
          className="text-center py-8"
          style={{ color: "var(--text-muted)" }}
        >
          No conversations yet
        </div>
      </div>
    </section>
  );
}

export default ChatSidebar;
