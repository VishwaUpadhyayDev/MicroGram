const ProfileBio = ({ isEditing, profileData, setProfileData }) => {
  return (
    <div className="space-y-3 w-full text-center lg:text-left">
      {isEditing ? (
        <>
          <input
            type="text"
            placeholder="Display Name"
            value={profileData.displayName}
            onChange={(e) => setProfileData(prev => ({ ...prev, displayName: e.target.value }))}
            className="w-full px-3 py-2 rounded-lg border text-sm bg-[var(--bg-light)] border-[var(--border)] text-[var(--text)]"
          />
          <textarea
            placeholder="Write a bio..."
            value={profileData.bio}
            onChange={(e) => setProfileData(prev => ({ ...prev, bio: e.target.value }))}
            rows={3}
            className="w-full px-3 py-2 rounded-lg border resize-none text-sm bg-[var(--bg-light)] border-[var(--border)] text-[var(--text)]"
          />
        </>
      ) : (
        <>
          {profileData.displayName && (
            <div className="font-semibold text-[var(--text)]">
              {profileData.displayName}
            </div>
          )}
          {profileData.bio && (
            <div className="text-sm whitespace-pre-wrap text-[var(--text)]">
              {profileData.bio}
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default ProfileBio;