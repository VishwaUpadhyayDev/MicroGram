import { Edit3 } from "lucide-react";

const ProfileActions = ({
  isEditing,
  setIsEditing,
  handleSave,
  loading,
  user,
  setProfileData,
}) => {
  return (
    <div className="flex gap-2 mt-6 w-full justify-center lg:justify-start">
      {!isEditing ? (
        <button
          onClick={() => setIsEditing(true)}
          className="flex items-center gap-2 px-6 py-1.5 rounded-lg font-medium text-sm border transition-colors bg-[var(--bg-light)] border-[var(--border)] text-[var(--text)]"
        >
          <Edit3 size={14} />
          Edit Profile
        </button>
      ) : (
        <>
          <button
            onClick={handleSave}
            disabled={loading}
            className="px-6 py-1.5 rounded-lg font-medium text-sm disabled:opacity-50 bg-[var(--primary)] text-[var(--bg-light)]"
          >
            {loading ? "Saving..." : "Save"}
          </button>
          <button
            onClick={() => {
              setIsEditing(false);
              setProfileData({
                displayName: user?.displayName || "",
                bio: user?.bio || "",
              });
            }}
            className="px-6 py-1.5 rounded-lg font-medium text-sm border bg-[var(--bg-light)] border-[var(--border)] text-[var(--text)]"
          >
            Cancel
          </button>
        </>
      )}
    </div>
  );
};

export default ProfileActions;
