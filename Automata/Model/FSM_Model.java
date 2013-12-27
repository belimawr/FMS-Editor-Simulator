package Automata.Model;

import Automata.Figures.CountingFigure;

import java.util.*;


/**
 * Author: Tiago de França Queiroz
 * Date: 21/12/13
 *
 * Copyright Tiago de França Queiroz, 2013.
 *
 * This file is part of Automata.
 *
 * Automata is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Automata is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Automata. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Figures are not directly connected to nodes
 * as they are part of the view, however nodes
 * have got reference to figures, so the best
 * (and efficient) way of finding a node from
 * the figure is using a hashtable.
 */
public class FSM_Model
{
	private static boolean valid;

	private static FSM_Node start;

	private Map<CountingFigure, FSM_Node> nodes;

	/*
	 * Singleton design pattern.
	 * It is used because different classes
	 * and objects need to access the same data
	 * structure.
	 */
	public static final FSM_Model me = new FSM_Model();

	public FSM_Model()
	{
		valid = false;
		start = null;

		nodes = new HashMap<CountingFigure, FSM_Node>();
	}

	public void insert(CountingFigure key, FSM_Node node)
	{
		nodes.put(key, node);
		System.out.printf("FSM_Model: (%s, %s) inserted\n", key, node);
	}

	public boolean connectOne(CountingFigure start, CountingFigure end)
	{
		FSM_Node nstart, nend;

		nstart = nodes.get(start);
		nend = nodes.get(end);

		nstart.setOne(nend);

		System.out.printf("1: \"%s\"->\"%s\"", start, end);
		return true;
	}

	public boolean connectZero(CountingFigure start, CountingFigure end)
	{
		FSM_Node nstart, nend;

		nstart = nodes.get(start);
		nend = nodes.get(end);

		nstart.setZero(nend);

		System.out.printf("1: \"%s\"->\"%s\"", start, end);
		return true;
	}

	public boolean isValid()
	{
		Collection<FSM_Node> states = nodes.values();
		if(valid)
			return true;
		else
		{
			for(FSM_Node n: states)
				if(!n.isValid())
				{
					valid = false;
				}
			valid = true;
		}
		return valid;
	}

	public static FSM_Model getInstance()
	{
		return me;
	}

	public void print_debug()
	{
		int i = 0;
		Collection<FSM_Node> my_nodes = nodes.values();


		for(FSM_Node n: my_nodes)
				System.out.printf("[%2d] - %s\n", i++, n);

		System.out.printf("I am valid: %s", isValid());
	}
}
