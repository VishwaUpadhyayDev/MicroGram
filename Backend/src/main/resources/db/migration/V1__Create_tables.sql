-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    provider VARCHAR(255) NOT NULL,
    provider_id VARCHAR(255),
    role VARCHAR(255) NOT NULL,
    display_name VARCHAR(100),
    bio TEXT,
    profile_picture_url VARCHAR(500),
    is_public BOOLEAN NOT NULL DEFAULT true,
    follower_count INTEGER NOT NULL DEFAULT 0,
    following_count INTEGER NOT NULL DEFAULT 0,
    post_count INTEGER NOT NULL DEFAULT 0,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP
);

-- Create posts table
CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    content TEXT,
    image_url VARCHAR(500),
    likes_count INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_posts_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create likes table
CREATE TABLE likes (
    id BIGSERIAL PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_likes_post_id FOREIGN KEY (post_id) REFERENCES posts(id),
    CONSTRAINT fk_likes_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE(post_id, user_id)
);

-- Create comments table
CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    comment_content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comments_post_id FOREIGN KEY (post_id) REFERENCES posts(id),
    CONSTRAINT fk_comments_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);