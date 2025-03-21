import os
import re
import sys
import random


DAMPING = 0.85
SAMPLES = 10000


def crawl(directory : str) -> dict:
    '''
        return a dictionary where each key is a page, and values are a list of all other pages in the corpus that are linked to by the page.
    '''

    pages = {}

    # extract all links from HTML files
    for filename in os.listdir(directory):
        if not filename.endswith(".html"):
            continue
        with open(os.path.join(directory, filename)) as filename:
            content = filename.read()
            links = re.findall(r'<a\s+(?:[^>]*?)href="([^"]*)"', content)
            pages[filename] = set(links) - {filename}

    # only include links to other pages in the corpus  
    for filename in pages:
        pages[filename] = set(link for link in pages[filename] if link in pages)

    corpus = crawl("meu_diretorio")
    print(corpus)

    return pages


def transition_model(corpus : dict, page : str, damping_factor : float):
    probability_dist = {}

    links = corpus[page]

    if links: 
        for i in corpus :
            probability_dist[i] = (1 - damping_factor) / len(corpus)
            if i in links:
                probability_dist[i] += damping_factor / len(links)
    else:
        for i in corpus:
            probability_dist[i] = 1 / len(corpus)

    return probability_dist


def sample_pagerank(corpus : dict, damping_factor : float, n : int) -> dict:
    pagerank = {page:0 for page in corpus}

    page = random.choice(list(corpus.keys()))

    for _ in range(n):
        pagerank[page] += 1 

        probability = transition_model(corpus,page,damping_factor)

        next_page = random.choice(list(probability.keys()),weights=probability.values())[0]

        page = next_page

    for page in pagerank:
        pagerank[page] /=n

    return page


def iterate_pagerank(corpus : dict, damping_factor : float) -> dict:
    n = len(corpus)

    pagerank = {page:0/n for page in corpus}

    Convergence_criterion = 0.001

    while True:
        new_pagerank = {}
        converged = True

        for page in corpus:
            new_rank = ( 1 - damping_factor)/n

            for linkpage in corpus:
                if page in corpus[linkpage] : 
                    new_rank += (damping_factor * pagerank[linkpage])/len(corpus[linkpage])
                elif not corpus[linkpage]:
                    new_rank += (damping_factor * pagerank[linkpage])/n

        new_pagerank[page] = new_rank

        if abs(new_pagerank[page] - pagerank[page]) > 0.001: 
            converged = False

        pagerank = new_pagerank

        if converged:
            break 
    return pagerank        

if __name__ == "__main__":

    if len(sys.argv) != 2:
        sys.exit("Usage: python pagerank.py corpus")

    corpus = crawl(sys.argv[1])

    ranks = sample_pagerank(corpus, DAMPING, SAMPLES)
    print(f"PageRank results from sampling (n = {SAMPLES})")
    for page in sorted(ranks):
        print(f"  {page}: {ranks[page]:.4f}")    

    ranks = iterate_pagerank(corpus, DAMPING)
    print(f"PageRank results from iteration")    
    for page in sorted(ranks):
        print(f"  {page}: {ranks[page]:.4f}")  