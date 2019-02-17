INSERT INTO users(full_name, email, password, created_at) VALUES ('Test User', 'test@mail.com', '$2a$10$7u448CoTvAYxbtlT3ZfWLem0WiEzhanqsWlo9cd9yRLs/aQWLJsfa', current_timestamp ) ON CONFLICT DO NOTHING;

INSERT
INTO
  workout(name, user_id)
VALUES (
  'Morning stretch',
  (SELECT id FROM users WHERE email='test@mail.com')
),
(
  'Yoga',
  (SELECT id FROM users WHERE email='test@mail.com')
)  ON CONFLICT DO NOTHING;

INSERT
INTO
  scheduled_workout(workout_id, done, time)
VALUES (
  (SELECT id FROM workout WHERE name='Morning stretch' LIMIT 1),
  false,
  current_timestamp + interval '1 day'
),
(
  (SELECT id FROM workout WHERE name='Yoga' LIMIT 1),
  true,
  current_timestamp + interval '2 week'
),(
  (SELECT id FROM workout WHERE name='Morning stretch' LIMIT 1),
  true,
  current_timestamp + interval '1 week'
),(
  (SELECT id FROM workout WHERE name='Morning stretch' LIMIT 1),
  true,
  current_timestamp + interval '8 day'
) ON CONFLICT DO NOTHING;

INSERT
INTO
  exercise(name, reps, sets, user_id)
  VALUES
    ('squat', 5, 4, (SELECT id FROM users WHERE email='test@mail.com')),
    ('sit-up', 3, 10, (SELECT id FROM users WHERE email='test@mail.com'))
    ON CONFLICT DO NOTHING;
