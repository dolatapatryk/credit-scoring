scores = ['low', 'medium', 'high']

counter = 0
for qualitative in scores:
    for financial in scores:
        for diff in scores:
            print(f"RULE {counter+1} : IF qualitative_score IS {qualitative} AND financial_score IS {financial} AND "
                  f"income_installment_difference IS {diff} THEN credit_score IS ;")
            counter += 1
