CREATE TABLE notifications (
    id UUID PRIMARY KEY,
    message TEXT NOT NULL,
    send_at TIMESTAMP NOT NULL
);
