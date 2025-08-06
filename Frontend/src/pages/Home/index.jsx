import BubbleBackground from "../../components/BubbleBackground";
import Desktop from "../../components/Desktop/Desktop";
import Mobile from "../../components/Mobile/Mobile";

function Home() {
  return (
    <main className="h-full overflow-hidden bg-[var(--bg)] relative">
      {/* <BubbleBackground bubbleCount={8} /> */}
      <Desktop />
      <Mobile />
    </main>
  );
}

export default Home;
