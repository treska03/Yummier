import React, { useEffect, useState, useCallback, useRef } from 'react';
import { Link } from 'react-router-dom';
import recipeService from '../service/recipeService';
import capitalizeFirstLetter from '../helper/capitalize';
import categoryToEmoji from '../helper/categoryToEmoji';

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
  totalPages: number;
  totalElements: number;
  last: boolean;
  size: number;
  number: number;
  sort: {
    sorted: boolean;
    empty: boolean;
    unsorted: boolean;
  };
  numberOfElements: number;
  first: boolean;
  empty: boolean;
}

const RecipeList = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [categoryFilter, setCategoryFilter] = useState('ALL');
  const [difficultyFilter, setDifficultyFilter] = useState('ALL');
  const [minReviewFilter, setMinReviewFilter] = useState(0);
  const [maxTimeNeededFilter, setMaxTimeNeededFilter] = useState(120);

  const [recipes, setRecipes] = useState<Recipe[]>([]);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);


  const fetchedPages = useRef(new Set<number>());

  const fetchRecipes = useCallback(async (pageNumber: number) => {
    try {
      setLoading(true);

      let queryParams = `?page=${pageNumber}`;
      if (searchTerm){
        queryParams += `&title=${searchTerm}`;
      }
      if (categoryFilter !== 'ALL') {
        queryParams += `&category=${categoryFilter}`;
      }
      if (difficultyFilter !== 'ALL') {
        queryParams += `&difficulty=${difficultyFilter}`;
      }
      if (minReviewFilter > 0) {
        queryParams += `&minReview=${minReviewFilter}`;
      }
      if (maxTimeNeededFilter < 120) {
        queryParams += `&maxTimeNeeded=${maxTimeNeededFilter}`;
      }

      const data = await recipeService.getAllRecipes(queryParams);
      if (pageNumber === 0) setRecipes(data.content);
      else setRecipes((prevRecipes) => [...prevRecipes, ...data.content]);

      setHasMore(!data.last);
    } catch (error) {
      console.error('Error fetching recipes:', error);
    } finally {
      setLoading(false);
    }
  }, [searchTerm,categoryFilter, difficultyFilter, minReviewFilter, maxTimeNeededFilter]);

  useEffect(() => {
    fetchedPages.current.clear();
    setPage(0);
  }, [searchTerm, categoryFilter, difficultyFilter, minReviewFilter, maxTimeNeededFilter]);


  useEffect(() => {
    if (fetchedPages.current.has(page)) return;
    fetchedPages.current.add(page);
    fetchRecipes(page);
  }, [fetchRecipes, page]);


  const handleScroll = useCallback(() => {
    const scrollTop = window.scrollY;
    const windowHeight = window.innerHeight;
    const scrollHeight = document.documentElement.scrollHeight;
    
    if (scrollTop + windowHeight >= scrollHeight - 50 && !loading && hasMore) {
      setPage((prevPage) => prevPage + 1);
    }
  }, [loading, hasMore]);

  useEffect(() => {
    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, [handleScroll]);


  const handleDelete = async (id: number) => {
    try {
      if (window.confirm('Are you sure you want to delete this recipe?')) {
        await recipeService.deleteRecipe(id);
        setRecipes((prevRecipes) => prevRecipes.filter((recipe) => recipe.id !== id));
        console.log('Recipe deleted');
      }
    } catch (error) {
      console.error('Error deleting recipe:', error);
    }
  };

  return (
    <div>
      <h2 className="mb-4 text-center">Our Favorite Recipes</h2>

      {/* Search bar */}
      <div className="search-container mb-4">
        <div className="input-group">
          <span className="input-group-text bg-white">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              className="bi bi-search"
              viewBox="0 0 16 16"
            >
              <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
            </svg>
          </span>
          <input
            type="text"
            className="form-control"
            placeholder="Search recipes by name..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
      </div>

      {/* Filters */}
      <div className="filters-container mb-4 d-flex justify-content-center gap-3">
        <select
          className="form-select"
          value={categoryFilter}
          onChange={e => setCategoryFilter(e.target.value)}
        >
          <option value="ALL">All Categories</option>
          <option value="BREAKFAST">Breakfast</option>
          <option value="LUNCH">Lunch</option>
          <option value="DINNER">Dinner</option>
          <option value="SNACK">Snack</option>
        </select>
        <select
          className="form-select"
          value={difficultyFilter}
          onChange={e => setDifficultyFilter(e.target.value)}
        >
          <option value="ALL">All Difficulties</option>
          <option value="EASY">Easy</option>
          <option value="MEDIUM">Medium</option>
          <option value="HARD">Hard</option>
        </select>
      </div>
      <div className="filters-container mb-4 d-flex justify-content-center gap-3">

      <div className="star-rating">
    {[1, 2, 3, 4, 5].map((gradeValue) => (
      <button
        type="button"
        name="grade"
        key={gradeValue}
        onClick={() => {
          // Toggle the selected rating
          setMinReviewFilter(prev => prev === gradeValue ? 0 : gradeValue);
        }}
        className="star-btn"
        style={{ backgroundColor: 'transparent', border: 'none', cursor: 'pointer' }}
      >
        {minReviewFilter >= gradeValue ? '★' : '☆'}
      </button>
    ))}
  </div>


        <div className="filter-slider">
          <label htmlFor="maxTimeNeeded">Max Time Needed (min)</label>
          <input
            type="range"
            id="maxTimeNeeded"
            min="0"
            max="120"
            step="5"
            value={maxTimeNeededFilter}
            onChange={(e) => setMaxTimeNeededFilter(parseInt(e.target.value))}
          />
          <span>{maxTimeNeededFilter}</span>
        </div>
      </div>


      {/* Recipe Cards */}
      <div className="row">
        {recipes.length === 0 ? (
          <div className="col-12 text-center">
            <p className="text-muted">No recipes found matching your search.</p>
          </div>
        ) : (
          recipes.map((recipe) => (
            <div key={`${recipe.id}-${recipe.title}`} className="col-md-4 mb-4">
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
                          {recipe.difficulty === 'EASY' ? '👶' : '👨‍🍳'}{' '}
                          {capitalizeFirstLetter(recipe.difficulty.toLowerCase())} {" "}
                        </span>
                        •{" "} {categoryToEmoji(recipe.category)}
                         {capitalizeFirstLetter(recipe.category.toLocaleLowerCase())}
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
          ))
        )}
      </div>

      {loading && (
        <div className="text-center">
          <p>Loading more recipes...</p>
        </div>
      )}
    </div>
  );
};

export default RecipeList;