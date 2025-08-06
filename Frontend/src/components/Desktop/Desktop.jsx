import ChatSidebar from "../Chat/ChatSidebar";
import FeedColumn from "../Feed/FeedColumn";
import CreatePostSidebar from "../Post/CreatePostSidebar";

function Desktop() {
  return (
    <section className="hidden lg:grid grid-cols-[1fr_2fr_1fr] h-full">
      <ChatSidebar />
      <FeedColumn />
      <CreatePostSidebar />
    </section>
  );
}

export default Desktop;
