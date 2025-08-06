function CreatePostSidebar() {
  return (
    <aside className="border-l border-[var(--border)] bg-[var(--bg-light)] flex flex-col">
      <header className="p-4 border-b border-[var(--border)]">
        <h2 className="text-lg font-semibold" style={{ color: "var(--text)" }}>
          Create Post
        </h2>
      </header>
      <div className="flex-1 overflow-y-auto p-4">
        <textarea
          placeholder="What's on your mind?"
          className="w-full h-32 p-3 rounded-md border border-[var(--border)] resize-none bg-[var(--bg)]"
          style={{ color: "var(--text)" }}
        />
        <button
          className="w-full mt-3 py-2 px-4 rounded-md font-medium"
          style={{
            backgroundColor: "var(--primary)",
            color: "var(--bg-light)",
          }}
        >
          Post
        </button>
      </div>
    </aside>
  );
}

export default CreatePostSidebar;
