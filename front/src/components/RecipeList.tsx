import React, {useEffect, useState} from 'react'
import { Link } from 'react-router-dom'
import recipeService from '../service/recipeService'
import capitalizeFirstLetter from '../helper/capitalize'


interface Recipe {
  id: number;
  title: string;
  description: string;
  timeNeeded: number;
  difficulty: string;
  category: string;
  ingredients: string[];
  instructions: string[];
}

interface RecipePage {
  content: Recipe[];
  totalPages: number,
    totalElements: number,
    last: boolean,
    size: number,
    number: number,
    sort: {
        sorted: boolean,
        empty: boolean,
        unsorted: boolean
    },
    numberOfElements: number,
    first: boolean,
    empty: boolean
}


const RecipeList = () => {
  const [recipes, setRecipes] = useState<RecipePage>();
  useEffect(() => {
      const fetchRecipes = async () => {
          try {
              const data = await recipeService.getAllRecipes();
              setRecipes(data);
          } catch (error) {
              console.error('Error fetching recipes:', error);
          }
      };

      fetchRecipes();
  }, []);

  const handleDelete = async (id) => {
    try {
      if (window.confirm('Are you sure you want to delete this recipe?')) {
        await recipeService.deleteRecipe(id);
      }
        console.log('Recipe deleted');
    } catch (error) {
        console.error('Error deleting recipe:', error);
    }
};

  return (
    <div>
      <h2 className="mb-4 text-center">Our Favorite Recipes</h2>
      <div className="row">
        {recipes?.content.map((recipe) => (
          <div key={recipe.id} className="col-md-4 mb-4">
            <div className="card h-100">
              <div className="card-body d-flex flex-column">
              <button
                    className="delete-button"
                    onClick={() => handleDelete(recipe.id)}
                  >
                    ✕
                  </button>
                <h5 className="card-title text-center mb-3">{recipe.title}</h5>
                <p className="card-text flex-grow-1">{recipe.description}</p>
                <div className="mt-3">
                  <p className="card-text">
                    <small className="text-muted">
                      ⏱️ {recipe.timeNeeded} min. • 
                      <span className="ms-2">
                        {recipe.difficulty === 'EASY' ? '👶' : '👨‍🍳'} {capitalizeFirstLetter(recipe.difficulty.toLowerCase())}
                      </span>
                    </small>
                  </p>
                  <Link
                    to={`/recipe/${recipe.id}`}
                    className="btn btn-primary w-100"
                  >
                    View Recipe
                  </Link>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

export default RecipeList