# Simple Interest Microservice

Create a microservice that calculate and generates the list of payments of the **simple interest** of a credit that must be paid in n **terms** and in **weekly** form.

## Input

     {
	    "amount": Double,
	    "terms":Integer,
	    "rate":Double
	 }

## Output

    {
        [
            {
                "payment_number":Integer,
                "amount": Double,
                "payment_date":Date
            }
            …
        ]
    }

## Rules

- The max terms (weeks) were the payment can be paid is 52, the minimum
  should be 4.
- The rate should bigger than 1%, lesser than 100%.
- The amount should be more than $1.00, the max should be lesser than  
  $999,999.00
- The response should be expressed as an array of objects.


# REST API

The URL of REST API is:

> /api/credits/requests

Is a POST REST API.
