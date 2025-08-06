import { useSelector, useDispatch } from "react-redux";
import { setActiveTab } from "../../features/uiSlice";
import BottomNav from "../BottomNav";
import ChatSidebar from "../Chat/ChatSidebar";
import FeedColumn from "../Feed/FeedColumn";
import CreatePostSidebar from "../Post/CreatePostSidebar";

function Mobile() {
  const { user } = useSelector((state) => state.auth);
  const { activeTab } = useSelector((state) => state.ui);
  const dispatch = useDispatch();

  const handleSetActiveTab = (tab) => {
    dispatch(setActiveTab(tab));
  };

  return (
    <>
      <section className="lg:hidden h-full grid grid-rows-[1fr_max-content]">
        <div className="flex-1 overflow-hidden">
          {activeTab === "feed" && <FeedColumn />}
          {activeTab === "chats" && <ChatSidebar />}
          {activeTab === "create" && <CreatePostSidebar />}
        </div>
        <BottomNav activeTab={activeTab} setActiveTab={handleSetActiveTab} />
      </section>
    </>
  );
}

export default Mobile;
