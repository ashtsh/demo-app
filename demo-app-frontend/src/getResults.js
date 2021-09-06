import { useState, useEffect } from 'react';

import './App.css';

const range = (start, end, step = 1) => {
    let output = [];
    if (typeof end === 'undefined') {
      end = start;
      start = 0;
    }
    for (let i = start; i <= end; i += step) {
      output.push(i);
    }
    return output;
  };

const GetResults = () => {
  
    const [results,setResults] = useState([]);
    const [loading, setLoading] = useState(true);
    const [numberOfPages,setNumberOfPages] = useState(1);
    const [activeIndex, setActiveIndex] = useState(0);
    const [activeClassName, setActiveClassName] = useState("");
    useEffect(()=>{
        fetch('http://localhost:8080/get').then(response => {
            return response.json();
        }).then(data => {
            setLoading(false);
            setResults(data.content);
            setNumberOfPages(data.totalPages);

            console.log(data);
        });
    },[]);
   
   
    return (<div>
        {loading && <h2>Loading Data....</h2>}
        <table className="table">
        <thead>
        <tr>
          <th colspan="4">Latest Results</th>
         </tr> 
         </thead>
         <tbody>
            <tr>
                <th>Thumbnail</th>
                <th>Title</th>
                <th>Description</th>
                <th>Publish time</th>
            </tr>
           
            {results.map((result) => 
                <tr>
                    <td><img src={result.thumbnailUrl} alt="video thumbail"/></td>
                    <td>{result.title}</td>
                    <td>{result.description}</td>
                    <td>{new Date(result.utcpublishTimeString).toLocaleDateString() 
                    + " " +new Date(result.utcpublishTimeString).toLocaleTimeString() }</td> 
                    
                </tr>
                )}
             </tbody>   
        </table>
        <ul className="page-list"> 
           {   range(1,numberOfPages).map((num,idx) =>
              
               
               <li className={(num-1) === activeIndex ? "active" : ""}><a href="!#" onClick={(event) => {
                   
                event.preventDefault();
                setActiveIndex(num-1);  
                
                setLoading(true);   
                fetch('http://localhost:8080/'+(num-1)).then(response => {
                return response.json()
                }).then(data => {
                    setResults(data.content);
                    setLoading(false);
                })
                  
               }} >{num}</a></li>)
                }

        </ul>
       
        </div>);
}

export default GetResults;