export const recipeData = [
    {
      id: 1,
      title: "Classic Spaghetti Carbonara",
      description: "A creamy Italian pasta dish with eggs, cheese, pancetta, and black pepper",
      timeNeeded: "25",
      difficulty: "Medium",
      ingredients: [
        "400g spaghetti",
        "200g pancetta or guanciale",
        "4 large eggs",
        "100g Pecorino Romano cheese",
        "100g Parmigiano Reggiano",
        "Black pepper",
        "Salt"
      ],
      instructions: [
        "Bring a large pot of salted water to boil",
        "Cook spaghetti according to package instructions",
        "While pasta cooks, cut pancetta into small cubes",
        "In a bowl, whisk eggs and grated cheeses",
        "Cook pancetta until crispy",
        "Combine hot pasta with egg mixture and pancetta",
        "Season with black pepper and serve immediately"
      ]
    },
    {
      id: 2,
      title: "Classic Burger",
      description: "Juicy homemade beef burger with all the fixings",
      timeNeeded: "30",
      difficulty: "Easy",
      ingredients: [
        "500g ground beef",
        "4 burger buns",
        "1 onion",
        "4 lettuce leaves",
        "2 tomatoes",
        "4 cheese slices",
        "Salt and pepper"
      ],
      instructions: [
        "Form beef into 4 equal patties",
        "Season patties with salt and pepper",
        "Grill or fry patties to desired doneness",
        "Toast the buns",
        "Slice onions and tomatoes",
        "Assemble burgers with all ingredients",
        "Serve hot"
      ]
    },
    {
      id: 3,
      title: "Chocolate Chip Cookies",
      description: "Soft and chewy cookies with melty chocolate chips",
      timeNeeded: "25",
      difficulty: "Easy",
      ingredients: [
        "2 1/4 cups flour",
        "1 cup butter",
        "3/4 cup sugar",
        "3/4 cup brown sugar",
        "2 eggs",
        "2 cups chocolate chips",
        "1 tsp vanilla extract"
      ],
      instructions: [
        "Preheat oven to 375°F (190°C)",
        "Cream butter and sugars together",
        "Beat in eggs and vanilla",
        "Mix in flour and chocolate chips",
        "Drop spoonfuls onto baking sheets",
        "Bake for 10-12 minutes",
        "Cool on wire rack"
      ]
    }
  ]


  //funcion to add/delete for testing when backend is not running
  export const addRecipe = (recipe) => {
    const newId = Math.max(...recipeData.map(r => r.id)) + 1
    recipeData.push({ ...recipe, id: newId })
  }
  
  export const deleteRecipe = (id) => {
    const index = recipeData.findIndex(r => r.id === id)
    if (index !== -1) {
      recipeData.splice(index, 1)
    }
  }