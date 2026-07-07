INSERT INTO users (id, name, email, password, created_at, updated_at) VALUES
    ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'João Silva', 'joao@email.com', 'senha123', NOW(), NOW()),
    ('b2c3d4e5-f6a7-8901-bcde-f12345678901', 'Maria Santos', 'maria@email.com', 'senha456', NOW(), NOW()),
    ('c3d4e5f6-a7b8-9012-cdef-123456789012', 'Pedro Oliveira', 'pedro@email.com', 'senha789', NOW(), NOW())
ON CONFLICT (email) DO NOTHING;
