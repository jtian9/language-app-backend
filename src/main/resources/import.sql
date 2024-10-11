-- Pre-populate the Learner table
INSERT INTO learners (username) VALUES ('shuqingzou');
INSERT INTO learners (username) VALUES ('jtian9');

-- Pre-populate the SearchHistory table
INSERT INTO search_history (learner_id, search_result, search_term) VALUES (1, 'タピオカは、最近人気のある飲み物であるタピオカミルクティーに使われる小さな球状のデンプンです。', 'タピオカ');
INSERT INTO search_history (learner_id, search_result, search_term) VALUES (2, 'Il gatto di Marco ama sedersi vicino alla finestra della casa mentre legge un libro. Questo libro parla di un viaggio avventuroso che un amico ha fatto attraverso diversi paesi. Durante il viaggio, l''amico ha assaggiato cibo delizioso e ha bevuto acqua fresca da fonti naturali. Ogni mattina, il sole splendeva luminoso, rendendo ogni giornata perfetta per esplorare. Quando non era in viaggio, l''amico frequentava una scuola di musica, dove imparava a suonare diversi strumenti. La musica riempiva la casa di armonia e gioia, creando un ambiente accogliente per tutti, incluso il gatto.', 'gatto, casa, libro, viaggio, amico, cibo, acqua, sole, scuola, musica');
INSERT INTO search_history (learner_id, search_result, search_term) VALUES ( 1, '日本の食文化には多くの魅力的な料理があります。例えば、醤油を使った料理は非常に人気があります。醤油は、牛丼や卵か けご飯などの料理に欠かせない調味料です。牛丼は、甘辛い醤油ベースのタレで煮込んだ牛肉を白いご飯の上に乗せたもので、多くの人に愛されています。一方、卵かけご飯は、炊きたてのご飯に生卵を割り入れ、醤油を少し垂らして混ぜて食べるシンプルながらも美味しい料理です。これらの料理は、サイゼリヤのようなファミリーレストランではあまり見かけませんが、家庭や専門店で楽しむことができます。日本では、電車での移動が一般的で、就活中の学生たちは、面接や説明会に参加するために電車を利用して様々な場所を訪れます。忙しい日々の中で、手軽に食べられる牛丼や卵かけご飯は、彼らにとって便利な食事の選択肢となっています。', '醤油, 牛丼, 卵かけご飯, サイゼリヤ, 電車, 就活')
