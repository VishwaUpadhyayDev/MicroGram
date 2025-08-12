import { Camera } from 'lucide-react';

const ProfilePicture = ({ user }) => {
  return (
    <div className="relative mb-6">
      <div className="w-32 h-32 lg:w-40 lg:h-40 rounded-full flex items-center justify-center text-4xl font-bold bg-[var(--primary)] text-[var(--bg-light)]">
        {(user?.displayName || user?.username || 'U').charAt(0).toUpperCase()}
      </div>
      <button className="absolute bottom-2 right-2 p-2 rounded-full shadow-lg bg-[var(--bg-light)] border-2 border-[var(--border)]">
        <Camera size={16} className="text-[var(--text)]" />
      </button>
    </div>
  );
};

export default ProfilePicture;