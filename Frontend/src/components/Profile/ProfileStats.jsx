const ProfileStats = ({ user }) => {
  return (
    <div className="flex justify-center gap-8 mb-6 w-full">
      <div className="text-center">
        <div className="font-semibold text-[var(--text)]">
          {user?.postCount || 0}
        </div>
        <div className="text-sm text-[var(--text-muted)]">posts</div>
      </div>
      <div className="text-center">
        <div className="font-semibold text-[var(--text)]">
          {user?.followerCount || 0}
        </div>
        <div className="text-sm text-[var(--text-muted)]">followers</div>
      </div>
      <div className="text-center">
        <div className="font-semibold text-[var(--text)]">
          {user?.followingCount || 0}
        </div>
        <div className="text-sm text-[var(--text-muted)]">following</div>
      </div>
    </div>
  );
};

export default ProfileStats;