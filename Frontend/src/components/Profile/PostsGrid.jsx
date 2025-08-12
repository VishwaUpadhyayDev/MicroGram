const PostsGrid = () => {
  // Mock posts data
  const mockPosts = [
    {
      id: 1,
      imageUrl: "https://picsum.photos/300/300?random=1",
      likes: 45,
      comments: 12,
    },
    {
      id: 2,
      imageUrl: "https://picsum.photos/300/300?random=2",
      likes: 23,
      comments: 5,
    },
    {
      id: 3,
      imageUrl: "https://picsum.photos/300/300?random=3",
      likes: 67,
      comments: 18,
    },
    {
      id: 4,
      imageUrl: "https://picsum.photos/300/300?random=4",
      likes: 89,
      comments: 24,
    },
    {
      id: 5,
      imageUrl: "https://picsum.photos/300/300?random=5",
      likes: 34,
      comments: 8,
    },
    {
      id: 6,
      imageUrl: "https://picsum.photos/300/300?random=6",
      likes: 56,
      comments: 15,
    },
    {
      id: 1,
      imageUrl: "https://picsum.photos/300/300?random=7",
      likes: 45,
      comments: 12,
    },
    {
      id: 2,
      imageUrl: "https://picsum.photos/300/300?random=8",
      likes: 23,
      comments: 5,
    },
    {
      id: 3,
      imageUrl: "https://picsum.photos/300/300?random=9",
      likes: 67,
      comments: 18,
    },
    {
      id: 4,
      imageUrl: "https://picsum.photos/300/300?random=10",
      likes: 89,
      comments: 24,
    },
    {
      id: 5,
      imageUrl: "https://picsum.photos/300/300?random=11",
      likes: 34,
      comments: 8,
    },
    {
      id: 6,
      imageUrl: "https://picsum.photos/300/300?random=12",
      likes: 56,
      comments: 15,
    },
  ];

  return (
    <div className="grid grid-cols-3 gap-1 w-full auto-rows-fr">
      {mockPosts.map((post, index) => (
        <div
          key={`${post.id}-${index}`}
          className="aspect-square rounded-lg overflow-hidden cursor-pointer relative"
        >
          <img
            src={post.imageUrl}
            alt={`Post ${post.id}`}
            loading="lazy"
            className="w-full h-full object-cover"
          />
          <div className="absolute bottom-2 left-2 bg-opacity-70 rounded px-2 py-1">
            <div className="text-white text-xs font-medium">
              ❤️ {post.likes} 💬 {post.comments}
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default PostsGrid;
