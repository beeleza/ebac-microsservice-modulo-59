CREATE TABLE IF NOT EXISTS loans (
    id UUID NOT NULL,
    user_id UUID NOT NULL,
    book_id UUID NOT NULL,
    loan_date DATE NOT NULL,
    expected_return_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(20) NOT NULL,
    fine NUMERIC(10, 2) NOT NULL,
    CONSTRAINT pk_loans PRIMARY KEY (id)
);
