INSERT INTO users(full_name, email, password) VALUES ('Kenan Selimovic', 'Kenan@mail.com', '$2a$10$HyKWUMiuTT56b3VFhQONRu85KJMBVLYD5E8ZctETvOTJQXCAG.tQy') ON CONFLICT DO NOTHING;

INSERT
INTO
  workout(name, user_id)
VALUES (
  'Morning stretch',
  (SELECT id FROM users WHERE email='Kenan@mail.com')
),
(
  'Yoga',
  (SELECT id FROM users WHERE email='Kenan@mail.com')
)  ON CONFLICT DO NOTHING;

INSERT
INTO
  scheduled_workout(workout_id, done, time)
VALUES (
  (SELECT id FROM workout WHERE name='Morning stretch' LIMIT 1),
  false,
  '2018-12-05 20:02:58.628000'
),
(
  (SELECT id FROM workout WHERE name='Yoga' LIMIT 1),
  true,
  '2018-08-05 20:02:58.628000'
)  ON CONFLICT DO NOTHING;

INSERT
INTO
  exercise(name, reps, sets, user_id)
  VALUES
    ('squat', 5, 4, (SELECT id FROM users WHERE email='Kenan@mail.com')),
    ('sit-up', 3, 10, (SELECT id FROM users WHERE email='Kenan@mail.com'))
    ON CONFLICT DO NOTHING;
