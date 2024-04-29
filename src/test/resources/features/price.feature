Feature: the price can be get

  Scenario Outline: client makes call to GET /api/prices
    Given date <date>
    When the client calls api price
    Then the client receives status code of 200
    And the client receives price <price> and brand <brand>
    Examples:
      | date                | price | brand |
      | 2020-06-14T10:00:00 | 35.5 | "ZARA" |
      | 2020-06-14T16:00:00 | 25.45 | "ZARA" |
      | 2020-06-14T21:00:00 | 35.5 | "ZARA" |
      | 2020-06-15T10:00:00 | 30.5 | "ZARA" |
      | 2020-06-15T21:00:00 | 38.95 | "ZARA" |

  Scenario: client makes call to GET /api/prices out of date range
    Given date 2020-01-14T10:00:00
    When the client calls api price
    Then the client receives status code of 404