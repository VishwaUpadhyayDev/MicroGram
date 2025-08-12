import { Home } from 'lucide-react';

const ProfileHeader = ({ user }) => {
  return (
    <div className="flex items-center gap-3 mb-4">
      <h1 className="text-2xl font-light text-center lg:text-left text-[var(--text)]">
        {user?.username}
      </h1>
      <button 
        onClick={() => window.location.href = '/app/home'}
        className="p-2 rounded-full transition-colors bg-[var(--bg-light)] border border-[var(--border)]"
      >
        <Home size={16} className="text-[var(--text)]" />
      </button>
    </div>
  );
};

export default ProfileHeader;