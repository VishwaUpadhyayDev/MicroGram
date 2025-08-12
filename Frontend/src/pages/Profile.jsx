import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { updateUser } from "../features/authSlice";
import { useApi } from "../hooks/useApi";
import ProfilePicture from "../components/Profile/ProfilePicture";
import ProfileHeader from "../components/Profile/ProfileHeader";
import ProfileStats from "../components/Profile/ProfileStats";
import ProfileBio from "../components/Profile/ProfileBio";
import ProfileActions from "../components/Profile/ProfileActions";
import ProfileTabs from "../components/Profile/ProfileTabs";
import PostsGrid from "../components/Profile/PostsGrid";

const Profile = () => {
  const { user } = useSelector((state) => state.auth);
  const dispatch = useDispatch();
  const { loading, error, getProfile, updateProfile } = useApi();

  const [isEditing, setIsEditing] = useState(false);
  const [profileData, setProfileData] = useState({
    displayName: user?.displayName || "",
    bio: user?.bio || "",
  });

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const profile = await getProfile();
        setProfileData({
          displayName: profile.displayName || "",
          bio: profile.bio || "",
        });
        dispatch(updateUser(profile));
      } catch (err) {
        console.error("Failed to fetch profile:", err);
      }
    };

    fetchProfile();
  }, [getProfile, dispatch]);

  const handleSave = async () => {
    try {
      const updatedProfile = await updateProfile(profileData);
      dispatch(updateUser(updatedProfile));
      setIsEditing(false);
    } catch (err) {
      console.error("Failed to update profile:", err);
    }
  };
  if (!user) {
    return <div>Loading...</div>;
  }
  if (loading) {
    return <div>Loading...</div>;
  }
  if (error) {
    return <div>Error: {error}</div>;
  }
  return (
    <div className="grid grid-cols-1 lg:grid-cols-[1fr_2fr] gap-8 p-4 lg:h-full h-fit overflow-auto">
      <div className="flex flex-col items-center">
        <ProfilePicture user={user} />
        <ProfileHeader user={user} />
        <ProfileStats user={user} />
        <ProfileBio
          isEditing={isEditing}
          profileData={profileData}
          setProfileData={setProfileData}
        />
        <ProfileActions
          isEditing={isEditing}
          setIsEditing={setIsEditing}
          handleSave={handleSave}
          loading={loading}
          user={user}
          setProfileData={setProfileData}
        />
      </div>

      <div className="grid grid-cols-1 h-full overflow-auto">
        <ProfileTabs />
        <PostsGrid />
      </div>
    </div>
  );
};

export default Profile;
