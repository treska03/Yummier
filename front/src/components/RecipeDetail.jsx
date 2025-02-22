import React, { useState, useEffect, useRef,useCallback} from 'react';
import { useParams } from 'react-router-dom';
import recipeService from '../service/recipeService';
import reviewService from '../service/reviewService';
import capitalizeFirstLetter from '../helper/capitalize';
import categoryToEmoji from '../helper/categoryToEmoji';

function RecipeDetail() {
  const { id } = useParams();
  const [recipe, setRecipe] = useState();
  const [reviews, setReviews] = useState([]);
  const [newReview, setNewReview] = useState({
    recipeId: parseInt(id),
    content: '',
    grade: 0
  });
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const [loading, setLoading] = useState(false);
  
  const fetchedPages = useRef(new Set());
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewReview(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const fetchReviews = useCallback(async (pageNumber) => {
    try {
      setLoading(true);
      const reviewsData = await reviewService.getReviews(parseInt(id), pageNumber);
      if (pageNumber === 0) setReviews(reviewsData.content);
      else setReviews((prevReviews) => [...prevReviews, ...reviewsData.content]);
      
      setHasMore(!reviewsData.last);
    } catch (error) {
      console.error('Error fetching reviews:', error);
    } finally {
      setLoading(false);
    }
  }, [id]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const recipeData = await recipeService.getById(parseInt(id));
        setRecipe(recipeData);
        setPage(0);
        fetchedPages.current.clear();
      } catch (error) {
        console.error('Error fetching recipe:', error);
      }
    };

    fetchData();
  }, [id]);

  useEffect(() => {
      if (fetchedPages.current.has(page)) return;
      fetchedPages.current.add(page);
      fetchReviews(page);
    }, [fetchReviews, page]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await reviewService.addReview(parseInt(id), newReview);
      setPage(0);
      fetchedPages.current.clear();
      setNewReview({
        recipeId: parseInt(id),
        content: '',
        grade: 0
      });
    } catch (error) {
      console.error('Error submitting review:', error);
    }
  };

  const handleDelete = async (reviewId) => {
    try {
      await reviewService.deleteReview(parseInt(id), reviewId);
      setReviews(reviews.filter(review => review.id !== reviewId));
    } catch (error) {
      console.error('Error deleting review:', error);
    }
  };

  const handleLoadMore = () => {
    setPage(prevPage => prevPage + 1);
  };

  if (!recipe) {
    return <div>Recipe not found</div>
  }
  return (
    <div>
      {/* Recipe Details */}
      <div className="recipe-detail">
        <div className="text-center mb-4">
          <h2 className="mb-3">{recipe.title}</h2>
          <p className="lead">{recipe.description}</p>
          <div className="badge bg-light text-dark me-2">⏱️ {recipe.timeNeeded} min.</div>
          <div className="badge bg-light text-dark">
            {recipe.difficulty === 'EASY' ? '👶' : '👨‍🍳'} {capitalizeFirstLetter(recipe.difficulty.toLowerCase())} 
          </div>
          <div className='badge bg-light text-dark '>{categoryToEmoji(recipe.category)} {capitalizeFirstLetter(recipe.category.toLowerCase())} </div>
        </div>

        <div className="row mt-4">
          <div className="col-md-6">
            <h3>Ingredients</h3>
            <ul className="list-group">
              {recipe.ingredients.map((ingredient, index) => (
                <li key={index} className="list-group-item">
                  🥘 {ingredient}
                </li>
              ))}
            </ul>
          </div>
          
          <div className="col-md-6">
            <h3>Instructions</h3>
            <ol className="list-group list-group-numbered">
              {recipe.instructions.map((step, index) => (
                <li key={index} className="list-group-item">
                  {step}
                </li>
              ))}
            </ol>
          </div>
        </div>
      </div>

      <div className='py-4'></div>

      {/* Reviews for recipe */}
      <div className='reviews'>
        <div className='text-center mb-4'>
          <h2>Reviews</h2>
        </div>
        {/* Add Review Form */}
        <form onSubmit={handleSubmit} className="mb-5">
          <div className="mb-3">
            <label htmlFor="review-content" className="form-label">Your Review</label>
            <textarea
              className="form-control"
              id="review-content"
              rows="3"
              name="content"
              value={newReview.content}
              onChange={handleChange}
              required
            ></textarea>
          </div>
          <div className="mb-3">
            <label className="form-label">Rating</label>
            <div className="star-rating">
              {[1, 2, 3, 4, 5].map((gradeValue) => (
                <button
                  type="button"
                  name="grade"
                  value={newReview.grade}
                  key={gradeValue}
                  onClick={() => {
                    setNewReview(prev => ({
                      ...prev,
                      grade: prev.grade === gradeValue ? 0 : gradeValue
                    }));
                  }}
                  className="star-btn"
                >
                  {newReview.grade >= gradeValue ? '★' : '☆'}
                </button>
              ))}
            </div>
          </div>
          <button type="submit" className="btn btn-primary">Submit Review</button>
        </form>

        {/* Reviews List */}
        <div className="reviews-list">
          {reviews.map((review) => (
            <div key={review.id} className="card mb-3">
              <div className="card-body">
                <div className="d-flex justify-content-between align-items-center mb-2">
                  <div className="star-rating">
                    {[...Array(5)].map((_, index) => (
                      <span key={index} className="star">
                        {index < review.grade ? '★' : '☆'}
                      </span>
                    ))}
                  </div>
                  <button 
                    onClick={() => handleDelete(review.id)}
                    className="delete-button"
                  >
                    ✕
                  </button>
                </div>
                <p className="card-text">{review.content}</p>
              </div>
            </div>
          ))}
        </div>
        {/* Show More Reviews Button */}
        {hasMore && (
          <div className="text-center mt-4">
            <button onClick={handleLoadMore} className="btn btn-primary" disabled={loading}>
              {loading ? 'Loading...' : 'Show More'}
            </button>
          </div>
        )}
      </div>
    </div>
  )
}

export default RecipeDetail