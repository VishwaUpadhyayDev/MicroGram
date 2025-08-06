function BubbleBackground({ bubbleCount = 8 }) {
  return (
    <div className="absolute inset-0 pointer-events-none">
      {[...Array(bubbleCount)].map((_, i) => (
        <div
          key={i}
          className="absolute rounded-full aspect-square animate-bubble-rise"
          style={{
            backgroundColor: "var(--primary)",
            width: `${Math.random() * 40 + 20}px`,
            left: `${Math.random() * 100}%`,
            animationDelay: `${i * 0.5}s`,
            animationDuration: `${Math.random() * 3 + 10}s`,
            "--drift": `${(Math.random() - 0.5) * 100}px`,
          }}
        />
      ))}
    </div>
  );
}

export default BubbleBackground;
