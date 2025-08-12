import { Grid, Bookmark } from "lucide-react";

const ProfileTabs = () => {
  return (
    <div className="border-b border-[var(--border)] mb-6 sticky top-0 bg-[var(--bg)]">
      <div className="flex justify-center lg:justify-start">
        <button className="flex items-center gap-2 px-6 py-3 border-b-2 border-[var(--primary)] font-medium text-sm text-[var(--text)]">
          <Grid size={16} />
          POSTS
        </button>
        <button className="flex items-center gap-2 px-6 py-3 font-medium text-sm text-[var(--text-muted)]">
          <Bookmark size={16} />
          SAVED
        </button>
      </div>
    </div>
  );
};

export default ProfileTabs;
