import { useState } from 'react';

const SearchPage = () => {
    const [queryString,setQueryString] = useState("");
    const [isSubmitted,setIsSubmitted] = useState(false);
   // const [numberOfPages,setNumberOfPages] = useState(1);
    const [results,setResults] = useState([]);
    const inputChangeHandler = event => {
        
        setQueryString(event.target.value);
        
    }
    const submitHandler = (event) =>{
        event.preventDefault();
        setIsSubmitted(true);
        fetch('http://localhost:8080/search?query='+queryString).then(response => {
            return response.json();
        }).then(data => {
            setIsSubmitted(true);
            setResults(data);
        });
    }
    return (
        <div>
            <form className="search-form" onSubmit={submitHandler}>
                <input type="text" value={queryString} placeholder="Enter your query"
                onChange={inputChangeHandler}></input>
                <input type="submit" value="Search"></input>
            </form>
            {isSubmitted &&  
                <table className="table">
                <thead>
                <tr>
                  <th colSpan="4">Search Results</th>
                 </tr> 
                 </thead>
                 <tbody>
                    <tr>
                        <th>Thumbnail</th>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Publish time</th>
                    </tr>
                   
                    {results.map(result => 
                        <tr>
                            <td><img src={result.thumbnailUrl} alt="video thumbail" width="150" height="100"/></td>
                            <td>{result.title}</td>
                            <td>{result.description}</td>
                            <td>{result.publishTime}</td>
                        </tr>
                        )}
                       </tbody> 
                </table>
            
            }
            
        </div>
    );
}
export default SearchPage;