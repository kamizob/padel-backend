CREATE TABLE app_user
(
    id VARCHAR(50) PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    role VARCHAR(50) NOT NULL,
    is_verified BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE court
(
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100),
    is_active BOOLEAN DEFAULT TRUE,
    open_time TIME,
    close_time TIME,
    slot_minutes INT CHECK (slot_minutes > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CHECK (close_time > open_time)
);

CREATE TABLE booking
(
     id VARCHAR(50) PRIMARY KEY,
     user_id VARCHAR(50) REFERENCES app_user(id),
     court_id VARCHAR(50) REFERENCES court(id),
     start_time TIMESTAMP NOT NULL,
     end_time TIMESTAMP NOT NULL,
     is_active BOOLEAN DEFAULT TRUE,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     reminder_sent BOOLEAN DEFAULT FALSE
);
CREATE UNIQUE INDEX booking_unique_active
    ON booking (court_id, start_time, end_time)
    WHERE is_active = true;

CREATE TABLE system_config (
    id SERIAL PRIMARY KEY,
    admin_initialized BOOLEAN DEFAULT FALSE
);

INSERT INTO system_config(admin_initialized) VALUES (FALSE);
